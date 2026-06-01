"use client";

import { useState } from "react";
import { signUpContributor } from "@/lib/api/contributors/contributorSignUp";
import styles from "./subscribeContributorForm.module.css"

const initialFormState = {
    username: "",
    password: "",
    confirmPassword: "",
    email: "",
    firstName: "",
    lastName: "",
    birthYear: "",
    birthMonth: "",
    birthDay: "",
};

export default function SubscribeContributorForm() {

    const [formData, setFormData] = useState(initialFormState);
    const [error, setError] = useState<Record<string, string>>({});
    const [loading, setLoading] = useState(false);
    const [success, setSucces] = useState("");


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setSucces("");
        setError({});

        if (formData.password !== formData.confirmPassword) {
            setError({confirmPassword: "Password do not match"});
            setLoading(false);
            return;
        }


    try {
        await signUpContributor({
        ...formData,
        birthYear: Number(formData.birthYear),
        birthMonth: Number(formData.birthMonth),
        birthDay: Number(formData.birthDay),
    });

    setSucces("Signup was successful!");
    setFormData(initialFormState);
    setError({});
    

    } catch (err: any) {

        // field validaion errors
        if (err.errors) {
            setError(err.errors);
            return;
        }

        if (err.message === "You must be at least 18 years old to upload content") {
            setError({ birthDay: err.message });
            return;
        }
        
        setError({ general: err.message || "Somthing went wrong" });

    } finally {
        setLoading(false);
    }
};

    const handleChange = (
        e: React.ChangeEvent<HTMLInputElement>
        ) => {
        const { name, value } = e.target;

        setFormData((prev) => ({
        ...prev,
            [name] : value,
        }));

        setError((prev) => {
            const newError = { ...prev };
            delete newError[name];
            delete newError.general;
            return newError;
        });

    };

    return (

        <main className={styles.container}>
        <form onSubmit ={handleSubmit}>
            <div className={styles.formBox}>

                <h1> SignUp Contributor</h1>

                <div className={styles.field}>
                    <label htmlFor="username">Username</label>
                    <input
                        id="username"
                        name="username"
                        type="text"
                        value={formData.username}
                        onChange={handleChange}
                        required
                    />
                    {error.username && (<p className={styles.error}>{error.username}</p>)}
                </div>

                <div className={styles.field}>
                    <label htmlFor="password">Password</label>
                    <input
                        id="password"
                        name="password"
                        type="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                    {error.password && (<p className={styles.error}>{error.password}</p>)}
                </div>

                <div className={styles.field}>
                    <label htmlFor="confirmPassword">Confirm password</label>
                    <input
                        id="confirmPassword"
                        name="confirmPassword"
                        type="password"
                        value={formData.confirmPassword}
                        onChange={handleChange}
                        required
                    />
                    {error.confirmPassword && (<p className={styles.error}>{error.confirmPassword}</p>)}
                </div>

                <div className={styles.field}>
                    <label htmlFor="email">Email</label>
                    <input
                        id="email"
                        name="email"
                        type="text"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                    {error.email && (<p className={styles.error}>{error.email}</p>)}
                </div>

                <div className={styles.field}>
                    <label htmlFor="firstName">First name</label>
                    <input
                        id="firstName"
                        name="firstName"
                        type="text"
                        value={formData.firstName}
                        onChange={handleChange}
                        required
                    />
                    {error.firstName && (<p className={styles.error}>{error.firstName}</p>)}
                </div>

                <div className={styles.field}>
                    <label htmlFor="lastName">Last name</label>
                    <input
                        id="lastName"
                        name="lastName"
                        type="text"
                        value={formData.lastName}
                        onChange={handleChange}
                        required
                    />
                    {error.lastName && (<p className={styles.error}>{error.lastName}</p>)}
                </div>

                <div className={styles.field}>
                    <label htmlFor="birthYear">Birth year</label>
                    <input
                        id="birthYear"
                        name="birthYear"
                        type="number"
                        value={formData.birthYear}
                        onChange={handleChange}
                        required
                    />
                    {error.birthYear && (<p className={styles.error}>{error.birthYear}</p>)}
                </div>

                <div className={styles.field}>
                    <label htmlFor="birthMonth">Birth month</label>
                    <input
                        id="birthMonth"
                        name="birthMonth"
                        type="number"
                        value={formData.birthMonth}
                        onChange={handleChange}
                        required
                    />
                    {error.birthMonth && (<p className={styles.error}>{error.birthMonth}</p>)}
                </div>

                <div className={styles.field}>
                    <label htmlFor="birthDay">Birth day</label>
                    <input
                        id="birthDay"
                        name="birthDay"
                        type="number"
                        value={formData.birthDay}
                        onChange={handleChange}
                        required
                    />
                    {error.birthDay && (<p className={styles.error}>{error.birthDay}</p>)}
                </div>

            

            <button className = {styles.btn} type= "submit" disabled = {loading}> {loading ? "Signing up..." : "Sign up" }</button>
            {success && <p className={styles.success}>{success}</p>}
            {error.general && <p className={styles.error}>{error.general}</p>}

            </div>

        </form>
        </main>
    )
}