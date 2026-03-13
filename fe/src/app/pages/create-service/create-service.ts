import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-create-service',
  imports: [FormsModule, RouterLink],
  templateUrl: './create-service.html'
})
export class CreateService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  name = '';
  owner = '';
  submitting = signal(false);
  error = signal<string | null>(null);

  submit(): void {
    if (!this.name.trim() || !this.owner.trim()) return;
    this.submitting.set(true);
    this.error.set(null);
    this.http.post('/mgmt-service', { name: this.name.trim(), owner: this.owner.trim() }).subscribe({
      next: () => this.router.navigate(['/services']),
      error: () => {
        this.error.set('Failed to create service. Please try again.');
        this.submitting.set(false);
      }
    });
  }
}