import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { MgmtServiceResponse } from '../../api/model';

@Component({
  selector: 'app-service-delete',
  imports: [RouterLink],
  templateUrl: './service-delete.html'
})
export class ServiceDelete implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);

  private id!: string;

  readonly service = signal<MgmtServiceResponse | null>(null);
  readonly deleting = signal(false);
  readonly error = signal<string | null>(null);

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.http.get<MgmtServiceResponse>(`/mgmt-service/${this.id}`).subscribe({
      next: (data) => this.service.set(data)
    });
  }

  confirm(): void {
    this.deleting.set(true);
    this.error.set(null);
    this.http.delete(`/mgmt-service/${this.id}`).subscribe({
      next: () => this.router.navigate(['/services']),
      error: () => {
        this.error.set('Failed to delete service. Please try again.');
        this.deleting.set(false);
      }
    });
  }
}