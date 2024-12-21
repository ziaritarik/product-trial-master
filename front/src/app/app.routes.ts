import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ContactComponent } from "./contact/contact.component";
import { ProductListComponent } from "./products/features/product-list/product-list.component";
import { HomeComponent } from "./shared/features/home/home.component";

export const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'products/list', component: ProductListComponent },
  { path: 'contact', component: ContactComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}