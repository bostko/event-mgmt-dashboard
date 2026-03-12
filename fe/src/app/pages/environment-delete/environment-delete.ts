import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { API_BASE_URL } from '../../api-base-url.token';
import { MgmtEnvironmentResponse } from '../../api/model';

@Component({
  selector: 'app-environment-delete',
  imports: [RouterLink],
  templateUrl: './environment-delete.html'
})
export class EnvironmentDelete implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = inject(API_BASE_URL);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  private id!: string;

  readonly environment = signal<MgmtEnvironmentResponse | null>(null);
  readonly deleting = signal(false);
  readonly error = signal<string | null>(null);

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.http.get<MgmtEnvironmentResponse>(`${this.baseUrl}/mgmt-environment/${this.id}`).subscribe({
      next: (data) => this.environment.set(data)
    });
  }

  confirm(): void {
    this.deleting.set(true);
    this.error.set(null);
    this.http.delete(`${this.baseUrl}/mgmt-environment/${this.id}`).subscribe({
      next: () => this.router.navigate(['/environments']),
      error: () => {
        this.error.set('Failed to delete environment. Please try again.');
        this.deleting.set(false);
      }
    });
  }
}