import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AdminGuard } from './admin.guard';
import { UserGuard } from './user.guard';

@Injectable({
  providedIn: 'root'
})
export class CombinedGuard implements CanActivate {
  constructor(private adminGuard: AdminGuard, private userGuard: UserGuard, private router: Router) {}
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):boolean{ 
    if (this.adminGuard.canActivate(route, state) || this.userGuard.canActivate(route, state)) {
        return true;
    } 
    else 
    {
      this.router.navigate(['/error']);
      return false;
    }
    
  }
}