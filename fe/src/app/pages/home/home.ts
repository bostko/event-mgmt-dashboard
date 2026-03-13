import { Component } from '@angular/core';
import { ManageEnvironments } from '../manage-environments/manage-environments';
import { Events } from '../events/events';

@Component({
  selector: 'app-home',
  imports: [ManageEnvironments, Events],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home {}
