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
  showDialog = false;

  constructor(public authService:AuthService,private readonly router:Router) { }
  
  ngOnInit(): void {
    this.userRole = localStorage.getItem('userRole'); // Assuming this method returns 'USER' or 'ADMIN'
  }
  
  logout(): void {
    // Logic to handle logout
    this.showDialog = true;
  }

  onDialogConfirm(result: boolean): void {
    this.showDialog = false;
    if (result) {
    this.authService.loggedOut();
    this.router.navigate(['/'])
    }
  }
  
}
