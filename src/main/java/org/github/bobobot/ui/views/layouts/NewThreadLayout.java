package org.github.bobobot.ui.views.layouts;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import lombok.Data;
import org.github.bobobot.access.PermissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NewThreadLayout extends VerticalLayout implements View {

	@Autowired
	private ApplicationContext appContext;

	@Data
	private class ThreadFormContent {
		String title;
		String content;
		String imagePath;
	}

	@PostConstruct
	private void init() {
		Binder<ThreadFormContent> binder = new Binder<>(ThreadFormContent.class);

		FormLayout newThreadForm = new FormLayout();
		TextField threadTitleField = new TextField();
		binder.bind(threadTitleField, ThreadFormContent::getTitle, ThreadFormContent::setTitle);
		TextArea threadContentField = new TextArea();
		binder.bind(threadContentField, ThreadFormContent::getContent, ThreadFormContent::setContent);
	
	}

}
