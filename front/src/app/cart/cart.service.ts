import { Injectable } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItemsSubject = new BehaviorSubject<Product[]>([]);
  cartItems$ = this.cartItemsSubject.asObservable();

  addToCart(product: Product) {
    const currentItems = this.cartItemsSubject.value;
    this.cartItemsSubject.next([...currentItems, product]);
  }

  getCartItems() {
    console.warn(this.cartItemsSubject.value)
    return this.cartItemsSubject.value;
  }

  removeFromCart(product: Product) {
    const currentItems = this.cartItemsSubject.value;
    this.cartItemsSubject.next(currentItems.filter(item => item.id !== product.id));
  }
  getCartQuantity() {
    return this.cartItemsSubject.value.length;
  }

  
}