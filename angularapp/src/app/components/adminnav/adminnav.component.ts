import { AuthService } from 'src/app/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-adminnav',
  templateUrl: './adminnav.component.html',
  styleUrls: ['./adminnav.component.css']
})
export class AdminnavComponent implements OnInit {

  userRole: string;

  constructor(public authService:AuthService,private router:Router) { }
  
  ngOnInit(): void {
    this.userRole = localStorage.getItem('userRole'); // Assuming this method returns 'USER' or 'ADMIN'
  }
  
  logout(): void {
    // Logic to handle logout
    this.authService.loggedOut();
    console.log('User logged out');
    this.router.navigate(['/']);
  }
  
}
