import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { ProductCreateComponent } from './components/product-create/product-create.component';
import { AdminviewproductComponent } from './components/adminviewproduct/adminviewproduct.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: SignupComponent },
  {path:'addProduct',component:ProductCreateComponent},
  {path:'viewproduct',component:AdminviewproductComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
