import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginDTO } from 'src/app/models/loginDTO.model';
import { AuthService } from 'src/app/services/auth.service';
 
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  submitted: boolean = false;
  showDialog: boolean = false; // Controls login failure pop-up
 
  constructor(private readonly authService: AuthService, private readonly router: Router, private readonly formBuilder: FormBuilder) {}
 
  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }
 
  get f() {
    return this.loginForm.controls;
  }
 
  onSubmit(): void {
    this.submitted = true;
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe(
        (data) => {
          let user: LoginDTO = data;
          localStorage.setItem("userId", user.userId + "");
          localStorage.setItem("userRole", user.userRole + "");
          localStorage.setItem("token", user.token + "");
          this.router.navigate(['/home']);
        },
        (error) => {
          this.showDialog = true; // Show Bootstrap modal instead of alert
          console.error("Login failed: " + JSON.stringify(error));
        }
      );
    }
  }
  closePopup(): void {
    this.showDialog = false;
  }
 
  navigateToSignup(): void {
    this.router.navigate(['/signup']);
  }
}