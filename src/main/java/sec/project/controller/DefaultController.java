package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Entry;
import sec.project.domain.User;
import sec.project.repository.EntryRepository;
import sec.project.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Controller
public class DefaultController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntryRepository entryRepository;

    @PostConstruct
    public void init() {
        // Super stupid user repository: passwords stored in plain text!
        userRepository.save(new User("bob", "no"));
        userRepository.save(new User("joe", "hunter2"));
        userRepository.save(new User("sam", "a"));

        // Add some sample entries too
        entryRepository.save(new Entry("bob", "Bob's first entry", "This is a long text about something interesting."));
        entryRepository.save(new Entry("bob", "Second entry", "Bob got bored already."));
        entryRepository.save(new Entry("joe", "Joe's Burgers", "Yum."));
    }

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadForm() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitForm(Model model, HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        final List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("password", password);
                model.addAttribute("entries", fetchEntries(username));
                return "redirect:/entries";
            }
        }

        return "denied";
    }

    @RequestMapping(value = "/entries", method = RequestMethod.GET)
    public String showEntries(Model model, HttpServletRequest request) {
        final String username = (String) request.getSession().getAttribute("username");
        model.addAttribute("entries", fetchEntries(username));
        return "entries";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addEntry(HttpServletRequest request, @RequestParam String title, @RequestParam String text) {
        final String username = (String) request.getSession().getAttribute("username");
        entryRepository.save(new Entry(username, title, text));
        return "redirect:/entries";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteEntry(Model model, @RequestParam Integer number) {
        List<Entry> allEntries = entryRepository.findAll();
        for (Entry entry : allEntries) {
            if (entry.getNumber() == number) {
                entryRepository.delete(entry);
            }
        }
        return "redirect:/entries";
    }

    private List<Entry> fetchEntries(String username) {
        final List<Entry> allEntries = entryRepository.findAll();
        final LinkedList<Entry> userEntries = new LinkedList<>();

        // Find diary entries of this user
        for (Entry entry : allEntries) {
            if (entry.getUser().equals(username)) {
                userEntries.add(entry);
            }
        }

        return userEntries;
    }
}
