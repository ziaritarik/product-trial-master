import {
  Component,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { DialogModule } from 'primeng/dialog';
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { CartComponent } from "./cart/cart-shop/cart-shop.component";
import { CartService } from "./cart/cart.service";
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent,DialogModule,CartComponent]
})
export class AppComponent {
  isCartVisible = false;
  cartQuantity = 0;
  title = "ALTEN SHOP";

  constructor(private cartService: CartService) {}
  
  ngOnInit() {
    this.cartService.cartItems$.subscribe(() => {
      this.cartQuantity = this.cartService.getCartQuantity();
    });
  }

  showCart() {
    this.isCartVisible = true;
  }

  hideCart() {
    this.isCartVisible = false;
  }
}
