import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminviewproductComponent } from './adminviewproduct.component';

describe('AdminviewproductComponent', () => {
  let component: AdminviewproductComponent;
  let fixture: ComponentFixture<AdminviewproductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminviewproductComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminviewproductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
