import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminviewreviewsComponent } from './adminviewreviews.component';

describe('AdminviewreviewsComponent', () => {
  let component: AdminviewreviewsComponent;
  let fixture: ComponentFixture<AdminviewreviewsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminviewreviewsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminviewreviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
