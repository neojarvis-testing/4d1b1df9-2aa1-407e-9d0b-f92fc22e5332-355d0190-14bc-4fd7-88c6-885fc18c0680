import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserviewproductComponent } from './userviewproduct.component';

describe('UserviewproductComponent', () => {
  let component: UserviewproductComponent;
  let fixture: ComponentFixture<UserviewproductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserviewproductComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserviewproductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
