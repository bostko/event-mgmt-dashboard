import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { ManageServices } from './pages/manage-services/manage-services';
import { ManageEnvironments } from './pages/manage-environments/manage-environments';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'services', component: ManageServices },
  { path: 'environments', component: ManageEnvironments },
  { path: '**', redirectTo: '' },
];
