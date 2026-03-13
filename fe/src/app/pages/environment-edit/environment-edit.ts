import { Component, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { MgmtEnvironmentResponse } from '../../api/model';

@Component({
  selector: 'app-environment-edit',
  imports: [FormsModule, RouterLink],
  templateUrl: './environment-edit.html'
})
export class EnvironmentEdit implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  private id!: string;

  name = '';
  loading = signal(true);
  submitting = signal(false);
  error = signal<string | null>(null);

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.http.get<MgmtEnvironmentResponse>(`/mgmt-environment/${this.id}`).subscribe({
      next: (data) => {
        this.name = data.name;
        this.loading.set(false);
      }
    });
  }

  submit(): void {
    if (!this.name.trim()) return;
    this.submitting.set(true);
    this.error.set(null);
    this.http.put(`/mgmt-environment/${this.id}`, { name: this.name.trim() }).subscribe({
      next: () => this.router.navigate(['/environments']),
      error: () => {
        this.error.set('Failed to update environment. Please try again.');
        this.submitting.set(false);
      }
    });
  }
}
