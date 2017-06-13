package cs544.exercise8;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/books")
public class BookController {

    @Resource
    private IBookDao bookDao;

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("books", bookDao.getAll());
        return "bookList";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String get(@PathVariable int id, Model model) {
        model.addAttribute("book", bookDao.get(id));
        return "bookDetail";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String update(Book book, @PathVariable int id) {
        bookDao.update(id, book);
        return "redirect:/books";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(int bookId) {
        bookDao.delete(bookId);
        return "redirect:/books";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String add(Book book) {
        bookDao.add(book);
        return "redirect:/books";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addForm() {
        return "bookAdd";
    }
}
