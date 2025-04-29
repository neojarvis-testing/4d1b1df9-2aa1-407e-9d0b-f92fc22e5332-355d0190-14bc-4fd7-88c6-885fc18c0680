import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { ProductCreateComponent } from './components/product-create/product-create.component';
import { AdminviewproductComponent } from './components/adminviewproduct/adminviewproduct.component';
import { AdminnavComponent } from './components/adminnav/adminnav.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { AdminGuard } from './admin.guard';
import { UserGuard } from './user.guard';
import { ReviewComponent } from './components/review/review.component';
import { ErrorComponent } from './components/error/error.component';
import { OrderplacedComponent } from './components/orderplaced/orderplaced.component';
import { MyorderComponent } from './components/myorder/myorder.component';

const routes: Routes = [
  {path: '', component: LoginComponent },
  {path: 'home', component: HomePageComponent },
  {path:'add-product',component:ProductCreateComponent,canActivate:[AdminGuard]},
  {path:'order-placed',component:OrderplacedComponent,canActivate:[AdminGuard]},
  {path:'view-product',component:AdminviewproductComponent, canActivate:[AdminGuard, UserGuard]},
  {path:'review',component:ReviewComponent, canActivate:[AdminGuard, UserGuard]},
  {path:'admin',component:AdminnavComponent, canActivate:[AdminGuard]},
  {path:'my-product',component:AdminnavComponent, canActivate:[UserGuard]},
  {path:'user',component:MyorderComponent, canActivate:[UserGuard]},
  {path:'**',component:ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
