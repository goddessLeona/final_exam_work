"use client";

import { useRouter } from "next/navigation";
import { logout } from "@/lib/api/logout";
import styles from "./logout-button.module.css"

export default function LogoutButton() {

    const router = useRouter();

    const handleLogout = async () => {

        try {
            await logout();
            router.push("/login");

        } catch (err) {
            console.error("Logout failed", err);
        }
    };

    return (
        <button
            type="button"
            className={styles.btn}
            onClick={handleLogout}>
            Logout
        </button>
    );
}
