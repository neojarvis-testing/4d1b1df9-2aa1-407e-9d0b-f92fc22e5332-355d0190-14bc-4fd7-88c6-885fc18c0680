import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  form:FormGroup
  successMessage:string|null=null

  constructor(private readonly formbuilder:FormBuilder, private readonly router:Router, private readonly authService:AuthService) {
    this.form = this.formbuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@_.])[A-Za-z\d@_.]{8,}$/)]],
      confirmPassword:["",[Validators.required]],
      mobileNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      userRole:["USER"],
      username: ['', [Validators.required, Validators.pattern(/^\w*$/)]]
    },{validators:this.matchPassword});
   }

   matchPassword(formGroup:FormGroup){
    const password=formGroup.get('password')
    const confirmPassword=formGroup.get('confirmPassword')
    if(password.value!=confirmPassword.value){
    confirmPassword.setErrors({passwordMismatch:true})
    }
    else{
    confirmPassword.setErrors(null)
    }
  }

  register(){
    if(this.form.valid){
      console.log(this.form.value)
        this.authService.register(this.form.value).subscribe((data)=>{
          console.log(data)
          alert("Registration successful");
          this.router.navigate(['/'])
        },(error)=>{
          alert("Registration failed! user already exists")
        })
    }
    else{
      alert("Invalid user Input")
    }
  }
}
