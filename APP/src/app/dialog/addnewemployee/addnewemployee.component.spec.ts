import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddnewEmployeeComponent } from './addnewemployee.component';

describe('AddnewEmployeeComponent', () => {
  let component: AddnewEmployeeComponent;
  let fixture: ComponentFixture<AddnewEmployeeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddnewEmployeeComponent]
    });
    fixture = TestBed.createComponent(AddnewEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
