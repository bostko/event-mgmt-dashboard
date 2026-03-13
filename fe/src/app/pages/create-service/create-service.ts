import { Component, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { MgmtEnvironmentResponse } from '../../api/model';

@Component({
  selector: 'app-create-service',
  imports: [FormsModule, RouterLink],
  templateUrl: './create-service.html'
})
export class CreateService implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  name = '';
  owner = '';
  environmentId!: number;
  environment = signal<MgmtEnvironmentResponse | null>(null);
  submitting = signal(false);
  error = signal<string | null>(null);

  ngOnInit(): void {
    const envIdParam = this.route.snapshot.queryParamMap.get('environmentId');
    if (!envIdParam) throw new Error('environmentIsubmittingd query param is required');
    this.environmentId = +envIdParam;

    this.http.get<MgmtEnvironmentResponse>(`/mgmt-environment/${this.environmentId}`).subscribe({
      next: (data) => { this.environment.set(data); },
      error: () => { throw new Error(`Environment ${this.environmentId} not found`); }
    });
  }

  submit(): void {
    if (!this.name.trim() || !this.owner.trim()) return;
    this.submitting.set(true);
    this.error.set(null);
    this.http.post('/mgmt-service', { name: this.name.trim(), owner: this.owner.trim(), environmentId: this.environmentId }).subscribe({
      next: () => this.router.navigate(['/environments', this.environmentId]),
      error: () => {
        this.error.set('Failed to create service. Please try again.');
        this.submitting.set(false);
      }
    });
  }
}
