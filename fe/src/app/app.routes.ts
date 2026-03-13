import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { CreateEnvironment } from './pages/create-environment/create-environment';
import { EnvironmentPreview } from './pages/environment-preview/environment-preview';
import { EnvironmentEdit } from './pages/environment-edit/environment-edit';
import { EnvironmentDelete } from './pages/environment-delete/environment-delete';
import { CreateService } from './pages/create-service/create-service';
import { ServicePreview } from './pages/service-preview/service-preview';
import { ServiceEdit } from './pages/service-edit/service-edit';
import { ServiceDelete } from './pages/service-delete/service-delete';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'services/create', component: CreateService },
  { path: 'services/:id', component: ServicePreview },
  { path: 'services/:id/edit', component: ServiceEdit },
  { path: 'services/:id/delete', component: ServiceDelete },
  { path: 'environments/create', component: CreateEnvironment },
  { path: 'environments/:id', component: EnvironmentPreview },
  { path: 'environments/:id/edit', component: EnvironmentEdit },
  { path: 'environments/:id/delete', component: EnvironmentDelete },
  { path: '**', redirectTo: '' },
];
