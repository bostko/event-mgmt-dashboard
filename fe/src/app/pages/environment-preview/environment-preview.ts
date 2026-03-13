import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { MgmtEnvironmentResponse } from '../../api/model';

@Component({
  selector: 'app-environment-preview',
  imports: [RouterLink],
  templateUrl: './environment-preview.html'
})
export class EnvironmentPreview implements OnInit {
  private readonly http = inject(HttpClient);
  private readonly route = inject(ActivatedRoute);

  readonly environment = signal<MgmtEnvironmentResponse | null>(null);

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.http.get<MgmtEnvironmentResponse>(`/mgmt-environment/${id}`).subscribe({
      next: (data) => this.environment.set(data)
    });
  }
}
