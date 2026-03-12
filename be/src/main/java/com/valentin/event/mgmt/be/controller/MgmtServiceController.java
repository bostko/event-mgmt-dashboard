package com.valentin.event.mgmt.be.controller;

import com.valentin.mgmt.event.domain.MgmtService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MgmtServiceController {
    @PostMapping( "/mgmt-service")
    public MgmtService createMgmtService() {
        return new MgmtService("first", "http-service", "val");
    }
}
