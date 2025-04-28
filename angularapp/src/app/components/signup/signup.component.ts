import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  form:FormGroup
  successMessage:string|null=null

  constructor(private formbuilder:FormBuilder, private router:Router, private authService:AuthService) {
    this.form = this.formbuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@_.])[A-Za-z\d@_.]{8,}$/)]],
      confirmPassword:["",[Validators.required]],
      mobileNumber: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      userRole:["USER"],
      username: ['', [Validators.required, Validators.pattern(/^[A-Za-z0-9_]*$/)]]
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
  ngOnInit(): void {

  }
  register(){
    if(this.form.valid){
      console.log(this.form.value)
        this.authService.register(this.form.value).subscribe((data)=>{
          console.log(data)
          alert("Registration successful");
          this.router.navigate(['/login'])
        },(error)=>{
          alert("Registration failed! user already exists")
        })
    }
    else{
      alert("Invalid user Input")
    }
  }
}
