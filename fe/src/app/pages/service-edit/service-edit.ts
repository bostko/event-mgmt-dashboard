import { Component, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { MgmtEnvironmentResponse, MgmtServiceResponse, Page } from '../../api/model';

@Component({
  selector: 'app-service-edit',
  imports: [FormsModule, RouterLink],
  templateUrl: './service-edit.html'
})
export class ServiceEdit implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  private id!: string;

  name = '';
  owner = '';
  environmentId: number | null = null;
  environments = signal<MgmtEnvironmentResponse[]>([]);
  loading = signal(true);
  submitting = signal(false);
  error = signal<string | null>(null);

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.http.get<Page<MgmtEnvironmentResponse>>('/mgmt-environment/all').subscribe({
      next: (data) => this.environments.set(data.content)
    });
    this.http.get<MgmtServiceResponse>(`/mgmt-service/${this.id}`).subscribe({
      next: (data) => {
        this.name = data.name;
        this.owner = data.owner;
        this.environmentId = data.environmentId;
        this.loading.set(false);
      }
    });
  }

  submit(): void {
    if (!this.name.trim() || !this.owner.trim() || !this.environmentId) return;
    this.submitting.set(true);
    this.error.set(null);
    this.http.put(`/mgmt-service/${this.id}`, { name: this.name.trim(), owner: this.owner.trim(), environmentId: this.environmentId }).subscribe({
      next: () => this.router.navigate(['/services']),
      error: () => {
        this.error.set('Failed to update service. Please try again.');
        this.submitting.set(false);
      }
    });
  }
}