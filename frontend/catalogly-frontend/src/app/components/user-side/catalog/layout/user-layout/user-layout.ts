import { Component } from '@angular/core';
import { UserMenu } from "../user-menu/user-menu";
import { RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-user-layout',
  imports: [UserMenu, RouterOutlet],
  templateUrl: './user-layout.html',
  styleUrl: './user-layout.css',
})
export class UserLayout {

}
