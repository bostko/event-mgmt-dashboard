import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { MgmtEnvironmentResponse, Page } from '../../api/model';

@Component({
  selector: 'app-manage-environments',
  imports: [RouterLink],
  templateUrl: './manage-environments.html',
  styleUrl: './manage-environments.scss'
})
export class ManageEnvironments implements OnInit {
  private readonly http = inject(HttpClient);

  readonly environments = signal<MgmtEnvironmentResponse[] | null>(null);

  ngOnInit(): void {
    this.http.get<Page<MgmtEnvironmentResponse>>('/mgmt-environment/all').subscribe({
      next: (data) => this.environments.set(data.content)
    });
  }
}
