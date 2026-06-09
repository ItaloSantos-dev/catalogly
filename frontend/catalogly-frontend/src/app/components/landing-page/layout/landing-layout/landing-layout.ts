import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LandingHeader } from '../landing-header/landing-header';
import { LandingFooter } from '../landing-footer/landing-footer';
@Component({
  selector: 'app-landing-layout',
  imports: [RouterOutlet, LandingHeader, LandingFooter],
  templateUrl: './landing-layout.html',
  styleUrl: './landing-layout.css',
})
export class LandingLayout {

}
