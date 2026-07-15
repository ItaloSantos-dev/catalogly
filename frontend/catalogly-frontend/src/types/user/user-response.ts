import { UserRole } from "../enums/user-role";

export interface UserResponseDTO{
    firstName:string;
    lastName:string;
    role:UserRole
}