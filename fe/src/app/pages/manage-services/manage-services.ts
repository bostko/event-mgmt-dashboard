import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { API_BASE_URL } from '../../api-base-url.token';
import { MgmtServiceResponse } from '../../api/model';

@Component({
  selector: 'app-manage-services',
  imports: [RouterLink],
  templateUrl: './manage-services.html',
  styleUrl: './manage-services.scss'
})
export class ManageServices implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = inject(API_BASE_URL);

  readonly services = signal<MgmtServiceResponse[] | null>(null);

  ngOnInit(): void {
    this.http.get<MgmtServiceResponse[]>(`${this.baseUrl}/mgmt-service/all`).subscribe({
      next: (data) => this.services.set(data)
    });
  }
}