import { Component, inject } from '@angular/core';
import { AuthService } from "./auth.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ms-front';
  router = inject(Router);

  constructor(public authService: AuthService) { }

  onLogout(): void {
    this.authService.logout();
    this.router.navigate(['login']);
  }
}
