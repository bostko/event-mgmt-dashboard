import { Component } from '@angular/core';
import { ManageEnvironments } from '../manage-environments/manage-environments';

@Component({
  selector: 'app-home',
  imports: [ManageEnvironments],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home {}
