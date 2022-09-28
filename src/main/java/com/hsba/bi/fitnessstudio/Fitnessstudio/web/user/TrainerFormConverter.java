package com.hsba.bi.fitnessstudio.Fitnessstudio.web.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.user.Trainer;
import org.springframework.stereotype.Component;

@Component
public class TrainerFormConverter {

    TrainerForm toForm(Trainer trainer){
        TrainerForm form = new TrainerForm();
        form.setCourses(trainer.getCourses());
        form.setWorkingDays(trainer.getWorkingDays());
        return form;
    }

    Trainer update(Trainer trainer, TrainerForm form){
        trainer.setCourses(form.getCourses());
        trainer.setWorkingDays(form.getWorkingDays());
        return trainer;
    }

}
