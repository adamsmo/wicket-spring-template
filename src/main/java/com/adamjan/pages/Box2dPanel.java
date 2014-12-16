package com.adamjan.pages;

import org.apache.wicket.markup.head.*;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import java.nio.charset.StandardCharsets;

/**
 * Created by adam on 17.12.2014.
 */
public class Box2dPanel extends Panel {

    private final WebMarkupContainer wmc;

    public Box2dPanel(String id) {
        super(id);
        wmc = new WebMarkupContainer("div");
        wmc.setOutputMarkupId(true);
        add(wmc);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(new JavaScriptUrlReferenceHeaderItem("js/Box2dWeb-2.1.a.3.js", "Box2dWeb-2.1.a.3.js", false, StandardCharsets.UTF_8.name(), null));
        response.render(new JavaScriptUrlReferenceHeaderItem("js/Box2dPanel.js", "Box2dPanel.js", false, StandardCharsets.UTF_8.name(), null));
        response.render(OnLoadHeaderItem.forScript("init('" + wmc.getMarkupId() + "')"));
    }
}
