import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyreviewComponent } from './myreview.component';

describe('MyreviewComponent', () => {
  let component: MyreviewComponent;
  let fixture: ComponentFixture<MyreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyreviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
