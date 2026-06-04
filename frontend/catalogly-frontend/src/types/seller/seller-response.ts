import { UserRole } from "../enums/user-role";

export interface SellerResponseDTO{
    firstName:string;
    lastName:string;
    userRole:UserRole;
    phone:string;
}