import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';
import { ButtonModule } from 'primeng/button';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-cart-shop',
  templateUrl: './cart-shop.component.html',
  styleUrls: ['./cart-shop.component.scss'],
  standalone: true,
  imports: [CommonModule, ButtonModule]
})
export class CartComponent {
  cartItems$ = this.cartService.cartItems$;

  constructor(private cartService: CartService) {}

  removeFromCart(product: Product) {
    this.cartService.removeFromCart(product);
  }
}