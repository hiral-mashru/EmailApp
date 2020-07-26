package io.chat.chatApp.controller;

import io.chat.chatApp.ChatAppApplication;
import io.chat.chatApp.model.chatdata;
import io.chat.chatApp.repository.DataRepository;
import io.chat.chatApp.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import io.chat.chatApp.model.userinfo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

@Controller
@SessionAttributes("user")
public class ChatController {

    @Autowired
    DataRepository mydataRepository;

    @Autowired
    UserRepository userRepository;

    @ModelAttribute("user")
    public userinfo setUser(){
        return new userinfo();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Model model,HttpSession session) {
        if(session.getAttribute("umail") != "") {
            System.out.println(session.getAttribute("umail"));
            session.setAttribute("umail", "");
            if(session.getAttribute("umail") == ""){
                model.addAttribute("logout", "You are successfully logged out!");
            }
        }
        return "login";
    }

    int page;
    @PostMapping(path = "/chatBox")
    public String loginn(HttpSession session, Model model, @RequestParam String EmailID, @RequestParam String Password){
        String name = null;
        Iterable<userinfo> userlist = userRepository.findAll();
        boolean found = false;
        for(userinfo user : userlist){
            if(user.getEmailID().equals(EmailID) && user.getPassword().equals(Password)){
                System.out.println("email id: "+EmailID+"pswd: "+Password);
                name = user.getName();
                found=true;
                break;
            } else {
                found = false;
            }
        }
        if(found==true) {
            page = 1;
            int p = 1;
            session.setAttribute("umail", EmailID);
//            userinfo app = (userinfo)request.getSession().setAttribute("umail",EmailID);
            HashSet<String> from = new HashSet<>();
            Iterable<chatdata> chatdataList = mydataRepository.findAll();
            for (chatdata data : chatdataList) {
                if (data.getTooo().equals(EmailID)) {
                    from.add(data.getFromm());
                }
            }
            int admin = 0;
            if(EmailID.equals("admin@gmail.com") && Password.equals("Admin239")) {
                admin = 1;
                System.out.println("admin : "+admin);
            }
            System.out.println("from: " + from);
            System.out.println("page " + page);
            model.addAttribute("admin",admin);
            model.addAttribute("name", name);
            model.addAttribute("email", EmailID);
            model.addAttribute("setfrom", from);
            model.addAttribute("page", page);
            model.addAttribute("p", p);
//            recievedMsg(model, session);
            return "home";
        }
        else{
            model.addAttribute("error", "Invalid");
            return "errorPage";

        }
    }

    @RequestMapping(value = "/chatBox/admin", method = RequestMethod.GET)
    public String admin(Model model, HttpSession session){
        List<userinfo> array = new ArrayList<>();
        Iterable<userinfo> userList = userRepository.findAll();
        for(userinfo data : userList){
            userinfo cdata = new userinfo();
            cdata.setUID(data.getUID());
            cdata.setName(data.getName());
            cdata.setEmailID(data.getEmailID());
            array.add(cdata);
        }

//        System.out.println(array);
        model.addAttribute("array",array);
        return "admin";
    }

    @PostMapping(path = "/admin")
    public @ResponseBody String dltUser(@RequestParam String EmailID){
        int id;
        Iterable<userinfo> userlist = userRepository.findAll();
        for(userinfo user : userlist) {
            if (user.getEmailID().equals(EmailID)) {
                id = user.getUID();
                userRepository.deleteById(id);
                break;
            }
        }
        return "done";
    }

    @RequestMapping(value = "/chatBox/recievedMsg", method = RequestMethod.GET)
    public String recievedMsg(Model model, HttpSession session){
        page = 1;
        HashSet<String> from = new HashSet<>();
        Iterable<chatdata> chatdataList = mydataRepository.findAll();
        for(chatdata data : chatdataList){
            if(data.getTooo().equals(session.getAttribute("umail"))){
                from.add(data.getFromm());
            }
        }

        System.out.println("from mustttt: "+from);
//        model.addAttribute("name",name);
        model.addAttribute("email",session.getAttribute("umail"));
        model.addAttribute("setfrom",from);
        model.addAttribute("page",page);
        return "home";
    }

    @RequestMapping(value = "/chatBox/view_to_{from}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView viewMsg(HttpSession session, @PathVariable String from, Model model){
        ModelAndView modelAndView = new ModelAndView();
        List<chatdata> array = new ArrayList<>();
        Iterable<chatdata> chatdataList = mydataRepository.findAll();
        for(chatdata data : chatdataList){
            if(data.getTooo().equals(session.getAttribute("umail")) && data.getFromm().equals(from)){
                chatdata cdata = new chatdata();
                cdata.setChatt(data.getChatt());
                cdata.setDate(data.getDate());
                array.add(cdata);
            }
        }

        System.out.println(array);
        model.addAttribute("array",array);

        modelAndView.setViewName("chatting");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupp() {
        return "signup";
    }

    @RequestMapping(value = "/chatBox", method = RequestMethod.GET)
    public String chatbox() {
        return "home";
    }

    @PostMapping(path="/chatBoxx")
    public String signupp(Model model, @RequestParam String Name, @RequestParam String EmailID, @RequestParam String Password){

        Iterable<userinfo> userlist = userRepository.findAll();
        boolean found = false;
        for(userinfo user : userlist){
            if(user.getEmailID().equals(EmailID) || Password.length()>8){
                System.out.println("email id: "+EmailID);
                found=true;
                break;
            } else {
                found = false;
            }
        }

        if(found==false){
            userinfo ui = new userinfo();
            ui.setName(Name);
            ui.setEmailID(EmailID);
            ui.setPassword(Password);
            userRepository.save(ui);

            chatdata cd = new chatdata();
            Iterable<chatdata> chatdataList = mydataRepository.findAll();
            for(chatdata chatlist : chatdataList){
                if(chatlist.getFromm().equals(Name)){
                    model.addAttribute("To",chatlist.getTooo());
                    model.addAttribute("content",chatlist.getChatt());
                    model.addAttribute("date",chatlist.getDate());
                }
            }
            return "login";
        } else {
            model.addAttribute("error","Invalid");
            return "errorPage";
        }

    }

    @RequestMapping(value = "/chatBox/newMsg", method = RequestMethod.GET)
    public String newMsg(Model model, HttpSession session){
        model.addAttribute("email",session.getAttribute("umail"));
        return "newMsg";
    }

    @PostMapping(path = "/chatBox/newMsg")
    public String newMsgg(Model model,HttpSession session ,@RequestParam String To, @RequestParam String Chat) {
        Iterable<userinfo> user = userRepository.findAll();
        System.out.println("post newmsg email"+session.getAttribute("umail"));

        boolean found = false;
        for(userinfo cdt : user){
            if(cdt.getEmailID().equals(To)){
                found = true;
            }
        }
        if(found == true) {
            chatdata cd = new chatdata();
            cd.setFromm((String) session.getAttribute("umail"));
            System.out.println(session.getAttribute("umail"));
            cd.setTooo(To);
            System.out.println(To);
            cd.setChatt(Chat);
            mydataRepository.save(cd);
            System.out.println("cid: "+cd.getCID());
            System.out.println("From: "+cd.getFromm());
            System.out.println("To: "+cd.getTooo());
            System.out.println("chat: "+cd.getChatt());
            System.out.println("date: "+cd.getDate());
            System.out.println("done");
            model.addAttribute("sent","it has been sent");
            return "newMsg";
        } else {
            model.addAttribute("error", "invalid user!!");
            return "errorPage";
        }
    }

    @RequestMapping(value = "/chatBox/sentMsg")
    public String sentMsg(HttpSession session, Model model) {
//        System.out.println("cant be null "+user.getEmailID());
        System.out.println("get session "+session.getAttribute("umail"));

        page = 2;
        HashSet<String> to = new HashSet<>();
        Iterable<chatdata> chatdataList = mydataRepository.findAll();
        for(chatdata data : chatdataList){
            if(data.getFromm().equals(session.getAttribute("umail"))){
                to.add(data.getTooo());
            }
        }

            System.out.println("ciddate: "+to);
        model.addAttribute("email",session.getAttribute("umail"));
        model.addAttribute("setto",to);
        model.addAttribute("page",page);
        return "home";
    }

    @RequestMapping(value = "/chatBox/sent_to_{to}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView sentMsg(HttpSession session, @PathVariable String to, Model model){
        ModelAndView modelAndView = new ModelAndView();
        List<chatdata> array = new ArrayList<>();
        Iterable<chatdata> chatdataList = mydataRepository.findAll();
        for(chatdata data : chatdataList){
            if(data.getFromm().equals(session.getAttribute("umail")) && data.getTooo().equals(to)){
                chatdata cdata = new chatdata();
                cdata.setChatt(data.getChatt());
                cdata.setDate(data.getDate());
                array.add(cdata);
            }
        }

//        System.out.println(array);
        model.addAttribute("array",array);

        modelAndView.setViewName("chatting");
        return modelAndView;
    }

}
