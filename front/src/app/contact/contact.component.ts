import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ToastModule } from 'primeng/toast';
@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonModule, InputTextModule, InputTextareaModule, ToastModule]
})
export class ContactComponent {
  name: string = '';
  email: string  = '';
  message: string  = '';


  constructor(private messageService: MessageService) {}
  onSubmit(){
    if (this.message.length > 300) {
      this.messageService.add({ severity: 'error', summary: 'Erreur', detail: 'Le message doit être inférieur à 300 caractères.' });
      return;
    }

    // Handle form submission
    console.log('Form submitted', { email: this.email, message: this.message });
    this.messageService.add({ severity: 'success', summary: 'Succès', detail: 'Demande de contact envoyée avec succès.' });

    // Reset form
    this.email = '';
    this.message = '';
  }
}