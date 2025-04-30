import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginDTO } from 'src/app/models/loginDTO.model';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!:FormGroup
  submitted:boolean=false
  constructor(private authService:AuthService, private router:Router,private formBuilder:FormBuilder) { }
  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      password:['',[Validators.required]]
    })
  }
  get f(){
    return this.loginForm.controls;
  }
  onSubmit():void{
    this.submitted=true;
    if(this.loginForm.valid){
      this.authService.login(this.loginForm.value).subscribe((data)=>{
        let user:LoginDTO = data
        alert("Login successful.")
        localStorage.setItem("userId",user.userId+"")
        localStorage.setItem("userRole",user.userRole+"")
        localStorage.setItem("token",user.token+"")
        this.router.navigate(['/home'])
      },(error)=>{
        alert("Login failed")
        console.log("Error"+JSON.stringify(error))
      })
    }

  }
  navigateToSignup(): void {
    this.router.navigate(['/signup']);
  }
  
}
