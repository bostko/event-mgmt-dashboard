import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-create-environment',
  imports: [FormsModule, RouterLink],
  templateUrl: './create-environment.html'
})
export class CreateEnvironment {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  name = '';
  submitting = signal(false);
  error = signal<string | null>(null);

  submit() {
    if (!this.name.trim()) return;
    this.submitting.set(true);
    this.error.set(null);
    this.http.post('/mgmt-environment', { name: this.name.trim() }).subscribe({
      next: () => this.router.navigate(['/environments']),
      error: () => {
        this.error.set('Failed to create environment. Please try again.');
        this.submitting.set(false);
      }
    });
  }
}