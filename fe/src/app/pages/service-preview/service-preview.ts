import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { API_BASE_URL } from '../../api-base-url.token';
import { MgmtServiceResponse } from '../../api/model';

@Component({
  selector: 'app-service-preview',
  imports: [RouterLink],
  templateUrl: './service-preview.html'
})
export class ServicePreview implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = inject(API_BASE_URL);
  private readonly route = inject(ActivatedRoute);

  readonly service = signal<MgmtServiceResponse | null>(null);

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.http.get<MgmtServiceResponse>(`${this.baseUrl}/mgmt-service/${id}`).subscribe({
      next: (data) => this.service.set(data)
    });
  }
}