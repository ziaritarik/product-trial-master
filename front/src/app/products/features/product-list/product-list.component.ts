import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, signal } from "@angular/core";
import { FormsModule } from '@angular/forms';
import { CartService } from 'app/cart/cart.service';
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { PaginatorModule } from 'primeng/paginator';
const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [CommonModule, DataViewModule, CardModule, ButtonModule, DialogModule
    , ProductFormComponent,DropdownModule,FormsModule, PaginatorModule],
})
export class ProductListComponent implements OnInit {
  // Injection des services ProductsService et CartService
  private readonly productsService = inject(ProductsService);
  private readonly cartService = inject(CartService);

   // Déclaration des variables publiques utilisées dans le template
  public products: Product[] = [];
  public filteredProducts: Product[] = [];
  public categories: string[] = [];
  public selectedCategory: string = '';
  public totalRecords: number = 0;
  public rows: number = 10;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

    // Méthode appelée lors de l'initialisation du composant
  ngOnInit() {
    this.productsService.get().subscribe(
      (products: Product[]) => {
        // Logique pour gérer les produits récupérés
        this.products = products;
        this.totalRecords = products.length;
        this.categories = [...new Set(products.map(product => product.category))];
        this.filteredProducts = this.products.slice(0, this.rows); 
      },
      (error) => {
        if (error.status === 404) {
          console.error('Products not found');
        } else {
          console.error('An error occurred', error);
        }
      }
    );
  }
  // Méthode appelée lors du changement de catégorie
  onCategoryChange() {
    this.filteredProducts = this.selectedCategory
    ? this.products.filter(product => product.category === this.selectedCategory)
    : this.products;
  this.totalRecords = this.filteredProducts.length;
  }
  // Méthode appelée lors du changement de page
  onPageChange(event: any) {
    const start = event.first;
    const end = start + event.rows;
    this.filteredProducts = this.products.slice(start, end);
  }
  // Méthodes pour gérer les actions sur les produits
  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }
/* Logique pour mettre à jour un produit */
  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }
/* Logique pour supprimer un produit */
  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe();
  }
/* Logique pour sauvegarder un produit */
  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe((newProduct: Product) => {
        this.products.push(newProduct);
        this.onCategoryChange();
      });
    } else {
      this.productsService.update(product).subscribe((updatedProduct: Product) => {
        const index = this.products.findIndex(p => p.id === updatedProduct.id);
        if (index !== -1) {
          this.products[index] = updatedProduct;
        }
        this.onCategoryChange();
      });
    }
    this.closeDialog();
  }
 /* Logique pour annuler l'édition */
  public onCancel() {
    this.closeDialog();
  }
/* Logique pour fermer le dialogue */ 
  private closeDialog() {
    this.isDialogVisible = false;
  }
/* Logique pour ajouter un produit au panier */
  public onAddToCart(product: Product) {
    this.cartService.addToCart(product);
    this.cartService.getCartItems();
  }
/* Logique pour retirer un produit du panier */
  public removeFromCart(product: Product) {
    this.cartService.removeFromCart(product);
    this.cartService.getCartItems();
  }
  
}
