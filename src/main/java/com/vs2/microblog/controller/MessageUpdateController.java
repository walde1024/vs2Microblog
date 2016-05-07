package com.vs2.microblog.controller;

import com.google.gson.Gson;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by Walde on 07.05.16.
 */
@Controller
public class MessageUpdateController {

    @MessageMapping("/update")
    @SendTo("/message/update")
    public String greeting() throws Exception {
        return new Gson().toJson("{'update': 'New Messages available'}");
    }
}
