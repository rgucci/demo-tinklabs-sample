package com.russellgutierrez.demo.tinklabs.sample.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.russellgutierrez.demo.tinklabs.sample.injection.component.ApplicationComponent;
import com.russellgutierrez.demo.tinklabs.sample.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
