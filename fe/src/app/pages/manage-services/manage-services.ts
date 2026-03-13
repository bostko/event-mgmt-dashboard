import { Component, inject, Input, OnInit, signal } from '@angular/core';
import { NgTemplateOutlet } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { MgmtServiceResponse } from '../../api/model';

@Component({
  selector: 'app-manage-services',
  imports: [RouterLink, NgTemplateOutlet],
  templateUrl: './manage-services.html',
  styleUrl: './manage-services.scss'
})
export class ManageServices implements OnInit {
  private readonly http = inject(HttpClient);

  @Input() environmentId: number | null = null;

  readonly services = signal<MgmtServiceResponse[] | null>(null);

  ngOnInit(): void {
    this.http.get<MgmtServiceResponse[]>(`/mgmt-environment/${this.environmentId}/services`).subscribe({
      next: (data) => {
        this.services.set(data);
      }
    });
  }
}
