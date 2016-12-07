/**
 *
 * @author geloin
 * @date 2012-5-5 涓婂崍9:31:52
 */
package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;

import service.UserService;

/**
 *
 * @author geloin
 * @date 2012-5-5 涓婂崍9:31:52
 */

// @Controller
// @RequestMapping(value = "background")
// public class LoginController {
//
// @Resource(name = "menuService")
// private MenuService menuService;
//
// /**
// *
// *
// * @author geloin
// * @date 2012-5-5 涓婂崍9:33:22
// * @return
// */
// @RequestMapping(value = "to_login")
// public ModelAndView toLogin(HttpServletResponse response) throws Exception {
//
// Map<String, Object> map = new HashMap<String, Object>();
//
// List<Menu> result = this.menuService.find();
//
// map.put("result", result);
//
// return new ModelAndView("background/menu", map);
// }
@Controller
@RequestMapping("/CoreServlet")
public class LoginController {
    @Autowired
    private UserService userService;
    /*
     * @Autowired private SchoolService schoolService;
     *
     * @Autowired private SchoolPersonService schoolpersonService;
     *
     * @Autowired private NoticeService noticeService;
     */

    private String oldtitle;

    public int model = 0;

    @RequestMapping(value = "/test")
    @ResponseBody
    public PageInfo formnoticedetail(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> model = new HashMap<String, Object>();
        PageInfo aaaa = (PageInfo) userService.select();
        return aaaa;

    }

    @RequestMapping(value = "/peopleTest", method = RequestMethod.GET)
    public ModelAndView peopleTest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("people");
        return mv;

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView formnoticedetail1(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return new ModelAndView("index");

    }

}