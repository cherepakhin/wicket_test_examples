package se.crisp.wicket.examples;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

import java.io.Serializable;
import java.util.Arrays;

public class DropDownPage extends BasePage {

    static final String ONCHANGE_EVENT = "onchange";

    static final String FORM = "form";

    static final String DROPDOWN = "dropDown";

    static final String MESSAGE = "message";

    private Label label;

    static String[] choices = {"choice 1", "choice 2"};

    private static final Logger log = Logger.getLogger(DropDownPage.class);

    public DropDownPage() {
        // "add" в MarkupContainer.
        // DropDownPage extends BasePage extends WebPage extends Page extends MarkupContainer
        // FORM = "form", DropDownForm см. ниже в этом же файле
        // DropDownForm extends Form<DropDownFormObject>
        // extends Form<DropDownFormObject>
        add(new DropDownForm(FORM));
        addMessage();
    }

    private void addMessage() {
        label = new Label(MESSAGE, "начальное значение");
        label.setOutputMarkupId(true);
        add(label);
    }

    public class DropDownFormObject implements Serializable {
        private static final long serialVersionUID = 1L;

        String dropDown;

        @Override
        public String toString() {
            return dropDown == null ? "" : dropDown;
        }
    }

    // DropDownFormObject - простой объект, определен тут же, выше
    public class DropDownForm extends Form<DropDownFormObject> {

        private static final long serialVersionUID = 1L;

        public DropDownForm(String id) {
            super(id);
            DropDownFormObject model = new DropDownFormObject();
            setDefaultModel(new CompoundPropertyModel<DropDownFormObject>(model));
            DropDownChoice<String> dropDownChoice = new DropDownChoice<String>(DROPDOWN, Arrays.asList(choices));

            // добавление реакции на событие
            // "add()" определено в abstract org.apache.wicket.Component
            //   public Component add(Behavior... behaviors) {
            dropDownChoice.add(createAjaxBehavior());
            add(dropDownChoice);
        }

        // см. выше на 4 строки
        private AjaxFormComponentUpdatingBehavior createAjaxBehavior() {
            // реакция "ONCHANGE_EVENT" - это реакция на смену значения в Combo
            // ONCHANGE_EVENT = "onchange" определено тут же:
            //              static final String ONCHANGE_EVENT = "onchange"
            AjaxFormComponentUpdatingBehavior updatingBehavior = new AjaxFormComponentUpdatingBehavior(ONCHANGE_EVENT) {

                private static final long serialVersionUID = 1L;

                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    DropDownFormObject modelObject = (DropDownFormObject) getDefaultModelObject();
                    log.debug(String.format("onUpdate: %s", modelObject.toString()));
                    label.setDefaultModelObject(modelObject.toString());
                    ajaxUpdate(target, label);
                }
            };
            return updatingBehavior;
        }

        // переход на следующую страницу (RadioForm)
        @Override
        protected void onSubmit() {
            log.debug("onSubmit: " + getDefaultModelObjectAsString());
            setResponsePage(RadioGroupPage.class);
        }
    }

    protected void ajaxUpdate(AjaxRequestTarget target, Component comp) {
        target.add(comp);
    }

}
